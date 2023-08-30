package com.flowengine.server.model.flow.model;

/**
 * @Description:
 * @author yangzl 2023.1.28
 * @version 1.00.00
 * @history:
 */
public class StartFlowBO {
    /** 绑定的业务主键 */
    private String taskOpId ;
    /** 流程id */
    private String mainId ;
    /** 机构id */
    private String orgId ;
    /** 部门id */
    private String deptId ;
    /** 创建人主键 */
    private String createUserOpId;
    /**通常设为start**/
    private String key;

    public String getTaskOpId() {
        return taskOpId;
    }

    public void setTaskOpId(String taskOpId) {
        this.taskOpId = taskOpId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
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

    public String getCreateUserOpId() {
        return createUserOpId;
    }

    public void setCreateUserOpId(String createUserOpId) {
        this.createUserOpId = createUserOpId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
