package demo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

@Controller
class GreetingController {
	static final String TEMPLATE = "Hello, %s!";

	@RequestMapping("/greeting")
	@ResponseBody
	HttpEntity<Greeting> greeting(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {

		Greeting greeting = new Greeting(String.format(TEMPLATE, name));

		greeting.add(linkTo(methodOn(GreetingController.class).greeting(name))
				.withSelfRel());
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
	}

}

class Greeting extends ResourceSupport {

	private final String content;
	private final String _links = "sfsfsfds11211df";

	@JsonCreator
	public Greeting(String content) {
		this.content = content;
	}

	@JsonProperty("content")
	public String getContent() {
		return content;
	}

	@JsonProperty(value = "links")
	public String get_links() {
		return _links;
	}
}

@RestController
class MyRestController {

	@RequestMapping("rest")
	Parent rest() {

		Parent p = new Parent();

		Child c1 = new Child();
		c1.setParent(p);

		Child c2 = new Child();
		c2.setParent(p);

		p.setChilds(new ArrayList<Child>(Arrays.asList(c1, c2)));

		return p;
	}
	
	@RequestMapping("rest2")
	String rest2() {
		
		Parent p = new Parent();

		Child c1 = new Child();
		c1.setParent(p);

		Child c2 = new Child();
		c2.setParent(p);

		p.setChilds(new ArrayList<Child>(Arrays.asList(c1, c2)));
		
		return JSON.toJSONString(p);
	}

	@RequestMapping("rest3")
	String rest3() {

		Parent p = new Parent();

		Child c1 = new Child();
		c1.setParent(p);

		Child c2 = new Child();
		c2.setParent(p);

		p.setChilds(new ArrayList<Child>(Arrays.asList(c1, c2)));

		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create().toJson(p);
	}
}

class Parent {
	@Expose
	String name = "John";
	@Expose
	java.util.List<Child> childs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public java.util.List<Child> getChilds() {
		return childs;
	}

	public void setChilds(java.util.List<Child> childs) {
		this.childs = childs;
	}

}

class Child {

	@Expose
	String name = "John son";

	@JsonIgnore
	Parent parent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}
}
