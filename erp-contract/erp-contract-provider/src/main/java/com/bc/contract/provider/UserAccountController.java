package com.bc.contract.provider;

import com.bc.common.enums.ResponseMsg;
import com.bc.contract.entity.UserAccount;
import com.bc.contract.service.TokenService;
import com.bc.contract.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    TokenService tokenService;

    @ApiOperation(value = "新增个人签署账号", notes = "新增个人签署账号")
    @PostMapping(value = "")
    public ResponseEntity<String> addUserAccount(
            @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
            @ApiParam(name = "name", value = "姓名", required = true) @RequestParam String name,
            @ApiParam(name = "idType", value = "证件类型", defaultValue = "CRED_PSN_CH_IDCARD") @RequestParam String idType,
            @ApiParam(name = "idNumber", value = "证件号", required = true) @RequestParam String idNumber,
            @ApiParam(name = "mobile", value = "手机号码") @RequestParam String mobile,
            @ApiParam(name = "email", value = "邮箱地址") @RequestParam String email) {
        ResponseEntity<String> responseEntity;
        try {
            UserAccount userAccount = new UserAccount(userId, name, idType, idNumber, mobile, email);
            String token = tokenService.getToken();
            String accountId = userAccountService.addUserAccountToSignPlatform(token, userAccount);
            if (!StringUtils.isEmpty(accountId)) {
                userAccount.setAccountId(accountId);
                userAccountService.addUserAccount(userAccount);
                responseEntity = new ResponseEntity<>(ResponseMsg.ADD_SUCCESS.getResponseCode(), HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<>(ResponseMsg.ADD_ERROR.getResponseCode(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 注销个人签署账号
     *
     * @param accountId 个人签署账号ID
     * @return 注销结果
     */
    @ApiOperation(value = "注销个人签署账号", notes = "注销个人签署账号")
    @DeleteMapping(value = "/{accountId}")
    public ResponseEntity<String> deleteUserAccount(
            @ApiParam(name = "accountId", value = "个人签署账号ID", required = true) @PathVariable String accountId) {
        ResponseEntity<String> responseEntity;
        try {
            String token = tokenService.getToken();
            boolean result = userAccountService.deleteUserAccountFromSignPlatform(token, accountId);
            if (result) {
                userAccountService.deleteUserAccountByAccountId(accountId);
                responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_SUCCESS.getResponseCode(), HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_ERROR.getResponseCode(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}