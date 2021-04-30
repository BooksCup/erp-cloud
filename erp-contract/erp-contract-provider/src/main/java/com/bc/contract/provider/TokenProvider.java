package com.bc.contract.provider;

import com.bc.common.cons.Constant;
import com.bc.contract.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 电子合同API token
 *
 * @author zhou
 */
@Api("电子合同API token")
@RestController
@RequestMapping("token")
public class TokenProvider {

    @Resource
    TokenService tokenService;

    /**
     * 获取token
     *
     * @return token
     */
    @ApiOperation(value = "获取token", notes = "获取token")
    @GetMapping(value = "")
    public ResponseEntity<String> getToken() {
        ResponseEntity<String> responseEntity;
        try {
            String token = tokenService.getToken();
            responseEntity = new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(Constant.EMPTY_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
