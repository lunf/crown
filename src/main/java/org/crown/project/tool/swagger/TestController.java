package org.crown.project.tool.swagger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.crown.framework.web.controller.WebController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * swagger User test method
 *
 * @author Crown
 */
@Api("User information management")
@RestController
@RequestMapping("/test/user")
public class TestController extends WebController {

    private final static Map<Integer, UserEntity> users = new LinkedHashMap<>();

    static {
        users.put(1, new UserEntity(1, "admin", "admin123", "15888888888"));
        users.put(2, new UserEntity(2, "crown", "admin123", "15666666666"));
    }

    @ApiOperation("Get user list")
    @GetMapping("/list")
    public List<UserEntity> userList() {
        return new ArrayList<>(users.values());
    }

    @ApiOperation("Get user details")
    @ApiImplicitParam(name = "userId", value = "User ID", required = true, dataType = "int", paramType = "path")
    @GetMapping("/{userId}")
    public UserEntity getUser(@PathVariable Integer userId) {
        return users.get(userId);
    }

    @ApiOperation("New users")
    @PostMapping("/save")
    public void save(UserEntity user) {
        users.put(user.getUserId(), user);
    }

    @ApiOperation("Update user")
    @PutMapping("/update")
    public void update(UserEntity user) {
        users.remove(user.getUserId());
    }

    @ApiOperation("Delete user information")
    @ApiImplicitParam(name = "userId", value = "User ID", required = true, dataType = "int", paramType = "path")
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Integer userId) {
        users.remove(userId);
    }
}

@ApiModel("User entity")
class UserEntity {

    @ApiModelProperty("User ID")
    private Integer userId;

    @ApiModelProperty("Username")
    private String username;

    @ApiModelProperty("Password")
    private String password;

    @ApiModelProperty("User phone")
    private String mobile;

    public UserEntity() {

    }

    public UserEntity(Integer userId, String username, String password, String mobile) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
