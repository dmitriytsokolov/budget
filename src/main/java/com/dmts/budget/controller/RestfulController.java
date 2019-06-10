package com.dmts.budget.controller;

import com.dmts.budget.dto.UserDto;
import com.dmts.budget.entity.AuditingEntity;
import com.dmts.budget.entity.User;
import com.dmts.budget.repository.AuditingEntityRepository;
import com.dmts.budget.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.event.AfterCreateEvent;
import org.springframework.data.rest.core.event.BeforeCreateEvent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Validated
@RestController
public class RestfulController implements ApplicationEventPublisherAware {

    @Autowired
    private UserService service;

    @Autowired
    private AuditingEntityRepository auditingEntityRepository;

    private ApplicationEventPublisher publisher;

    @GetMapping("/user/{id}")
    @PreAuthorize("authentication.principal.username.equals(#id)")
    User getMyInfo(@PathVariable String id) {
        return service.findById(Long.parseLong(id));
    }

    @GetMapping("/userz")
    List<User> getAllUsers(Pageable page) {
        return service.findAll(page);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/findByEmail/{email}")
    UserDto findByEmail(@PathVariable String email) {
        return service.findByEmailQuery(email);
    }

    @GetMapping("/findByExample/{emailAgent}")
    List<User> findAllByExample(@PathVariable String emailAgent) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
                .withIgnorePaths("id")
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.endsWith());
        User user = new User();
        user.setEmail(emailAgent);
        return service.findAllByExample(Example.of(user, matcher));
    }

    @PostMapping("/create")
    void createUser(@RequestBody User user) {
        publisher.publishEvent(new BeforeCreateEvent(user));
        service.save(user);
        publisher.publishEvent(new AfterCreateEvent(user));
    }

    @GetMapping("/createSomeEntity/{param}")
    void createAuditingEntity(@PathVariable String param) {
        AuditingEntity auditingEntity = new AuditingEntity();
        auditingEntity.setSomeField(param);
        auditingEntityRepository.save(auditingEntity);
    }

    @GetMapping("/updateSomeEntity/{id}/{param}")
    void updateAuditingEntity(@PathVariable Long id, @PathVariable String param) {
        AuditingEntity auditingEntity = auditingEntityRepository.findById(id).get();
        auditingEntity.setSomeField(param);
        auditingEntityRepository.save(auditingEntity);
    }

    @GetMapping("/optimisticLocking/{id}/{param}")
    void optimisticLocking(@PathVariable Long id, @PathVariable String param) {
        //ExecutorService es = Executors.newFixedThreadPool(2);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Executor delegatedExecutor = Executors.newFixedThreadPool(10);
        Executor delegatingExecutor = new DelegatingSecurityContextExecutor(delegatedExecutor, securityContext);
        //1 thread
        delegatingExecutor.execute(() -> {
            AuditingEntity auditingEntity = auditingEntityRepository.findById(id).get();
            auditingEntity.setSomeField(param + "-1 thread");
            //little delay
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            auditingEntityRepository.save(auditingEntity);
            System.out.println("1 thread finished");
        });

        //2 thread
        delegatingExecutor.execute(() -> {
            AuditingEntity auditingEntity = auditingEntityRepository.findById(id).get();
            auditingEntity.setSomeField(param + "-2 thread");
            auditingEntityRepository.save(auditingEntity);
            System.out.println("2 thread finished");
        });
        //delegatingExecutor.shutdown();
        System.out.println("finished");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
