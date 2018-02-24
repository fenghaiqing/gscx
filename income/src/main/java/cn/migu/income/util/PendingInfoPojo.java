package cn.migu.income.util;

public class PendingInfoPojo {

	private String id;//	待办标识	String	忽略
	
	private String code;//	待办编码       	String	待办编码,各应用系统待办的唯一标识
	
	private String title;//[填写时，有要求吗？]	待办标题	String	待办标题
	
	private Long date	;//待办时间	Number	待办时间,时间戳12233333
	
	private String person	;//待办人标识	String	待办负责人标识,即用户登录名
	
	private String url;//	URL地址	String	待办信息URL-待办处理页面（需要考虑单点登录的情况） 
	
	private Integer status	;//待办状态	Number	待办状态  0:待办 ，1: 已办，2: 待阅, 3: 已阅
	
	private String oraniger;//	待办发起人标识	String	待办发起人标识
	
	private String previous;//	上一待办人	String	上一待办人
	
	private Integer type	;//公文或者人事	Number	待办类别:（0:全部，1:公文，2:人事,3:报账，4:合同5：工单）
	
	private String clazz;//种类	String	文种[有哪些枚举值？]
	
	private String ngPerson;//	拟稿人	String	拟稿人
	
	private String ngDept	;//拟稿部门	String	拟稿部门
	
	private String docNum	;//文号	String	文号eg:中建投发文XX号
	
	private Integer ngDate	;//拟稿日期	Number	时间戳1345566676
	
	private String rawData;//[新增参数]	客户端待办跳转参数	String	客户端待办跳转参数
	
	private String appCodeMobile;//[新增参数]	应用标识	String	公文：0001，休假：0003，访客：0004，会议室：0005，合同：0006，报账：0007
	
	private Integer isClient;//[新增参数]	是否支持客户端处理	Integer	1：支持  2:不支持

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOraniger() {
		return oraniger;
	}

	public void setOraniger(String oraniger) {
		this.oraniger = oraniger;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getNgPerson() {
		return ngPerson;
	}

	public void setNgPerson(String ngPerson) {
		this.ngPerson = ngPerson;
	}

	public String getNgDept() {
		return ngDept;
	}

	public void setNgDept(String ngDept) {
		this.ngDept = ngDept;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public Integer getNgDate() {
		return ngDate;
	}

	public void setNgDate(Integer ngDate) {
		this.ngDate = ngDate;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String getAppCodeMobile() {
		return appCodeMobile;
	}

	public void setAppCodeMobile(String appCodeMobile) {
		this.appCodeMobile = appCodeMobile;
	}

	public Integer getIsClient() {
		return isClient;
	}

	public void setIsClient(Integer isClient) {
		this.isClient = isClient;
	}


}
