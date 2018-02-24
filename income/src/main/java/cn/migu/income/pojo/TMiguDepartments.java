package cn.migu.income.pojo;

public class TMiguDepartments {

	private String deptCode;

    private String deptName;

    private String deptDescribe;

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getDeptDescribe() {
        return deptDescribe;
    }

    public void setDeptDescribe(String deptDescribe) {
        this.deptDescribe = deptDescribe == null ? null : deptDescribe.trim();
    }

	@Override
	public String toString() {
		return "TMiguDepartments [deptCode=" + deptCode + ", deptName=" + deptName + ", deptDescribe=" + deptDescribe
				+ "]";
	}
    
    
}