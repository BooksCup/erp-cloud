package com.bc.contract.provider;

import com.bc.common.enums.ResponseMsg;
import com.bc.contract.entity.UserAccount;
import com.bc.contract.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 个人签署账号
 *
 * @author zhou
 */
@Api("个人签署账号")
@RestController
@RequestMapping("userAccount")
public class UserAccountController {

    @Resource
    UserAccountService userAccountService;

    /**
     * 新增个人签署账号
     *
     * @return 新增结果
     */
    @ApiOperation(value = "新增个人签署账号", notes = "新增个人签署账号")
    @PostMapping(value = "")
    public ResponseEntity<String> addUserAccount(
            @ApiParam(name = "name", value = "姓名", required = true) @RequestParam String name,
            @ApiParam(name = "idType", value = "证件类型", defaultValue = "CRED_PSN_CH_IDCARD") @RequestParam String idType,
            @ApiParam(name = "idNumber", value = "证件号", required = true) @RequestParam String idNumber,
            @ApiParam(name = "mobile", value = "手机号码") @RequestParam String mobile,
            @ApiParam(name = "email", value = "邮箱地址") @RequestParam String email) {
        ResponseEntity<String> responseEntity;
        try {
            UserAccount userAccount = new UserAccount(name, idType, idNumber, mobile, email);
            userAccountService.addUserAccount(userAccount);
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
