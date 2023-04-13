package com.flowengine.server.model;

import com.flowengine.common.utils.entity.PublicUserEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author yangzl 2019-12-03
 * @version 1.00.00
 * @history:
 */
public class UserCache implements Serializable {

private static final long serialVersionUID = 1L;

	private String opId;

	private String userId;

	private String userPhone;

	private String userName;

	private String userPassword;

	private Date createTime;

	private String orgId;

	private String deptId;

	private Integer userCategory;

	private String refEntityId;

	private Integer isStop;

	private Date lastLogin;

	private Integer registerFrom;

	private Integer userSources;

	private String bgImg;

	private String userSex;

	private String headImage;

	private String userAddress;

	private String streetAddress;

	private Date userBirthday;

	private Integer userAge;

	private String userEmail;

	private String workAddress;

	private String accessToken ;
	/** 单位分钟 */
	private Integer accessTokenLimit ;
	/**  */
	private String refreshToken ;
	/** 单位天 */
	private Integer refreshTokenLimit ;

	private List<String> roleIds;

	public UserCache() {

	}

	public UserCache(PublicUserEntity userEntity) {

		this.bgImg = userEntity.getBgImg();
		this.createTime = userEntity.getCreateTime();
		this.headImage = userEntity.getHeadImage();
		this.isStop = userEntity.getIsStop();
		this.lastLogin = userEntity.getLastLogin();
		this.opId = userEntity.getOpId();
		this.orgId = userEntity.getOrgId();
		this.refEntityId = userEntity.getRefEntityId();
		this.registerFrom = userEntity.getRegisterFrom();
		this.streetAddress = userEntity.getStreetAddress();
		this.userAddress = userEntity.getUserAddress();
		this.userAge = userEntity.getUserAge();
		this.userBirthday = userEntity.getUserBirthday();
		this.userCategory = userEntity.getUserCategory();
		this.userEmail = userEntity.getUserEmail();
		this.userId = userEntity.getUserId();
		this.userName = userEntity.getUserName();
		this.userPhone = userEntity.getUserPhone();
		this.userSex = userEntity.getUserSex();
		this.userSources = userEntity.getUserSources();
		this.workAddress = userEntity.getWorkAddress();
		this.deptId = userEntity.getDeptId();
		this.accessToken = userEntity.getAccessToken();
		this.accessTokenLimit = userEntity.getAccessTokenLimit();
		this.refreshToken = userEntity.getRefreshToken();
		this.refreshTokenLimit = userEntity.getRefreshTokenLimit();
	}

	public void reCache(PublicUserEntity userEntity) {

		this.bgImg = userEntity.getBgImg();
		this.createTime = userEntity.getCreateTime();
		this.headImage = userEntity.getHeadImage();
		this.isStop = userEntity.getIsStop();
		this.lastLogin = userEntity.getLastLogin();
		this.opId = userEntity.getOpId();
		this.orgId = userEntity.getOrgId();
		this.refEntityId = userEntity.getRefEntityId();
		this.registerFrom = userEntity.getRegisterFrom();
		this.streetAddress = userEntity.getStreetAddress();
		this.userAddress = userEntity.getUserAddress();
		this.userAge = userEntity.getUserAge();
		this.userBirthday = userEntity.getUserBirthday();
		this.userCategory = userEntity.getUserCategory();
		this.userEmail = userEntity.getUserEmail();
		this.userId = userEntity.getUserId();
		this.userName = userEntity.getUserName();
		this.userPhone = userEntity.getUserPhone();
		this.userSex = userEntity.getUserSex();
		this.userSources = userEntity.getUserSources();
		this.workAddress = userEntity.getWorkAddress();
		this.deptId = userEntity.getDeptId();
		this.accessToken = userEntity.getAccessToken();
		this.accessTokenLimit = userEntity.getAccessTokenLimit();
		this.refreshToken = userEntity.getRefreshToken();
		this.refreshTokenLimit = userEntity.getRefreshTokenLimit();
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getAccessTokenLimit() {
		return accessTokenLimit;
	}

	public void setAccessTokenLimit(Integer accessTokenLimit) {
		this.accessTokenLimit = accessTokenLimit;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Integer getRefreshTokenLimit() {
		return refreshTokenLimit;
	}

	public void setRefreshTokenLimit(Integer refreshTokenLimit) {
		this.refreshTokenLimit = refreshTokenLimit;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Integer getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(Integer userCategory) {
		this.userCategory = userCategory;
	}

	public String getRefEntityId() {
		return refEntityId;
	}

	public void setRefEntityId(String refEntityId) {
		this.refEntityId = refEntityId;
	}

	public Integer getIsStop() {
		return isStop;
	}

	public void setIsStop(Integer isStop) {
		this.isStop = isStop;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Integer getRegisterFrom() {
		return registerFrom;
	}

	public void setRegisterFrom(Integer registerFrom) {
		this.registerFrom = registerFrom;
	}

	public Integer getUserSources() {
		return userSources;
	}

	public void setUserSources(Integer userSources) {
		this.userSources = userSources;
	}

	public String getBgImg() {
		return bgImg;
	}

	public void setBgImg(String bgImg) {
		this.bgImg = bgImg;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public Date getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
}
