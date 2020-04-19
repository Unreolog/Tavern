package telegram.infrastructure;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

@Component
public abstract class JacksonConfiguration {

	private static Logger log = LoggerFactory.getLogger(JacksonConfiguration.class);

	private static ObjectMapper mapper;
	private ApplicationContext application;
	private Environment environment;

	@Autowired
	public void setContext(ApplicationContext application) {
		this.application = application;
	}

	@Autowired
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@PostConstruct
	public void initialize() throws IOException {
		String[] profiles = environment.getActiveProfiles();
		log.info("Loading " + originalClass().getSimpleName() + ":");
		JacksonConfigurationSources source = findAnnotation(getClass(), JacksonConfigurationSources.class);


		if (source == null) {
			return;
		}

		for (String path : source.value()) {
			// Load default
			loadPath(path);

			// Load profiles
			for (String profile : profiles) {
				loadPath(path.replace(".yml", "-" + profile + ".yml"));
			}
		}
	}

	private Class<?> originalClass() {
		Class<?> toClass = getClass();
		if (Enhancer.isEnhanced(toClass)) {
			toClass = toClass.getSuperclass();
		}
		return toClass;
	}

	private void loadPath(String path) throws IOException {
		Resource[] resources = application.getResources(path);
		for (Resource resource : resources) {
			load(resource, getMapper());
		}
	}

	private boolean isTestResource(Resource resource) throws IOException {
		return resource.getURI().toString().contains("test-classes");
	}

	private boolean load(Resource resource, ObjectMapper mapper) throws IOException {
		log.debug("--- " + resource.getURI());
		InputStream stream = resource.getInputStream();
		mapper.readerForUpdating(this).readValue(stream);
		return true;
	}

	protected ObjectMapper getMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper(new YAMLFactory());
			mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
			//mapper.registerModule(new RequirementKeyJacksonModule());
		}
		return mapper;
	}
}
