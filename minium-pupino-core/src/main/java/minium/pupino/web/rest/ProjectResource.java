package minium.pupino.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import minium.pupino.domain.Project;
import minium.pupino.domain.SourceRepository;
import minium.pupino.domain.SourceRepository.Type;
import minium.pupino.repository.ProjectRepository;
import minium.pupino.service.JenkinsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing Project.
 */
@RestController
@RequestMapping("/app")
public class ProjectResource {

	private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

	private int i = 0;

	@Inject
	private ProjectRepository projectRepository;

	@Autowired
	private JenkinsService jenkinService;

	/**
	 * POST /rest/projects -> Create a new project.
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws JAXBException 
	 */
	@RequestMapping(value = "/rest/projects", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public void create(@RequestBody Project project) throws URISyntaxException, IOException, JAXBException {
		log.debug("REST request to save Project : {}", project);
		projectRepository.save(project);
		jenkinService.createJob(project.getName(),project.getSourceRepository().getType().toString(),project.getSourceRepository().getUrl().toString());
	}

	/**
	 * GET /rest/projects -> get all the projects.
	 */
	@RequestMapping(value = "/rest/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<Project> getAll() {
		log.debug("REST request to get all Projects");
		if (i == 0) {
			Project project = new Project();
			project.setDescription("sds");
			project.setName("cp-e2e-test");
			SourceRepository sr = new SourceRepository();
			sr.setType(Type.GIT);
			sr.setUrl("url@dsdsd.gti");
			project.setSourceRepository(sr);
			projectRepository.save(project);
			i = 1;
		}
		return projectRepository.findAll();
	}

	/**
	 * GET /rest/projects/:id -> get the "id" project.
	 */
	@RequestMapping(value = "/rest/projects/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Project> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get Project : {}", id);
		Project project = projectRepository.findOne(id);
		if (project == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(project, HttpStatus.OK);
	}

	/**
	 * DELETE /rest/projects/:id -> delete the "id" project.
	 */
	@RequestMapping(value = "/rest/projects/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public void delete(@PathVariable Long id) {
		log.debug("REST request to delete Project : {}", id);
		projectRepository.delete(id);
	}
}
