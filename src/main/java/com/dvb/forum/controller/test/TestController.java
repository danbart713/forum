package com.dvb.forum.controller.test;

import com.dvb.forum.entity.AdminEntity;
import com.dvb.forum.entity.BusinessEntity;
import com.dvb.forum.entity.IndividualEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @RequestMapping(path = "/testIndividual", method = RequestMethod.GET)
    public ResponseEntity<IndividualEntity> testIndividual(HttpServletRequest httpServletRequest) {
        log.info("TestController - testIndividual - httpServletRequest: {}", httpServletRequest);

        IndividualEntity individualEntity = (IndividualEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("TestController - testIndividual - individualEntity: {}", individualEntity);

        return new ResponseEntity<>(individualEntity, HttpStatus.OK);
    }

    @RequestMapping(path = "/testBusiness", method = RequestMethod.GET)
    public ResponseEntity<BusinessEntity> testBusiness(HttpServletRequest httpServletRequest) {
        log.info("TestController - testBusiness - httpServletRequest: {}", httpServletRequest);

        BusinessEntity businessEntity = (BusinessEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("TestController - testBusiness - businessEntity: {}", businessEntity);

        return new ResponseEntity<>(businessEntity, HttpStatus.OK);
    }

    @RequestMapping(path = "/testAdmin", method = RequestMethod.GET)
    public ResponseEntity<AdminEntity> testAdmin(HttpServletRequest httpServletRequest) {
        log.info("TestController - testAdmin - httpServletRequest: {}", httpServletRequest);

        AdminEntity adminEntity = (AdminEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("TestController - testAdmin - adminEntity: {}", adminEntity);

        return new ResponseEntity<>(adminEntity, HttpStatus.OK);
    }

}
