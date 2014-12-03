package com.midgetontoes.todolist.controller;

import com.midgetontoes.todolist.model.List;
import com.midgetontoes.todolist.resource.ListResource;
import com.midgetontoes.todolist.resource.ListResourceAssembler;
import com.midgetontoes.todolist.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@ExposesResourceFor(List.class)
@RequestMapping("/lists")
public class ListRestController {

    @Autowired
    private ListService listService;

    @RequestMapping(method = RequestMethod.GET)
    public Resources<ListResource> readLists(Principal principal) {
        String username = principal.getName();
        Link link = linkTo(ListRestController.class).withSelfRel();
        return new Resources<ListResource>(new ListResourceAssembler().toResources(listService.findByUserUsername(username)), link);
    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.GET)
    public ListResource readList(@PathVariable Long listId) {
        return new ListResourceAssembler().toResource(listService.findOne(listId));
    }
}
