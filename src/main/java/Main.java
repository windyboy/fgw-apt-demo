
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.util.Properties;

/**
* 发改委项目编号接口api
* java 样例程序
* 演示 如何使用java从接口获得项目编码信息，写入项目编码信息
* 演示 如何获得jwt token，以及使用token
* 
* 演示程序把调用接口需要的参数保存在properties文件当中
* jwt的token每次获取以后，有效时间为12个小时，可以重复使用
* 不需要每次使用的时候重新获取
*/
public class Main{

	public static final MediaType JSON
      = MediaType.parse("application/json; charset=utf-8");
    //系统参数properties
    private static final String PROP_FILE = "main.properties";
    //jwt token 
	private static final String ACTION_TOKEN = "token";
	//样例运行参数， 执行get project接口
	private static final String ACTION_GET = "get";
	//样例运行参数, 执行put project接口
	private static final String ACTION_PUT = "put";
	//获取token的用户名
	private static final String P_USERNAME = "username";
	//获取toekn的用户密码
	private static final String P_PASSWORD = "password";
	//系统接口地址
	private static final String P_URL = "url";
	//jwt token string
	private static final String P_TOKEN = "token";

	private Properties prop = new Properties();

	private String baseurl;

	private String token;

	private String username;

	private String password;

	private OkHttpClient c = new OkHttpClient();

	public void getToken() throws IOException{
		System.out.println("----------- get user token string by username and password ------------");
    	//token 服务url
    	String url = this.baseurl + "token";
    	System.out.println("target url "+url);
    	//token 服务参数 json
    	String json = "{\"User\":\""+username+"\", \"Pass\":\""+password+"\"}";
    	System.out.println("post json body");
    	System.out.println("------------------------");
    	System.out.println(json);
    	System.out.println("------------------------");
    	RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
    		.url(url)
    		//使用http post
    		.post(body)
    		.build();
    	Response response = c.newCall(request).execute();
    	System.out.println("response "+response);
    	System.out.println("------------Token String------------");
    	token = response.body().string();
    	System.out.println(token);
    	System.out.println("------------------------------------");
    	writeToProp("token", token);
	}

	public void getProject(String code) throws IOException {
		//从properties文件获取token
		token = getFromProp(P_TOKEN);
		if (token == null || "".equals(token)) {
			getToken();
		}
		// System.out.println("token "+token);
		System.out.println("----------- get project by code ------------");
		//项目编码写在url上
		String url = this.baseurl + "project/" + code;
    	System.out.println("target url "+url);
    	//jwt 授权 header生成
    	String header = "Bearer "+token;
    	Request request = new Request.Builder()
    		.url(url)
    		//在http header里增加授权信息
    		.header("Authorization", header)
    		.build();
    	System.out.println(request);
    	System.out.println("-------------headers-----------");
    	System.out.println(request.headers());
    	System.out.println("-------------------------------");
    	Response response = c.newCall(request).execute();
    	System.out.println("response "+response);
    	System.out.println("------------Project Info------------");
    	System.out.println(response.body().string());
    	System.out.println("------------------------------------");
	}

	public void putProject(String code, String sbbh) throws IOException {
		//从properties文件获取token
		token = getFromProp(P_TOKEN);
		if (token == null || "".equals(token)) {
			getToken();
		}
		// System.out.println("token "+token);
		System.out.println("----------- put project by code ------------");
		//项目编码写在url上
		String url = this.baseurl + "project/" + code;
    	System.out.println("target url "+url);
    	String json = "{\"Sbbh\":\""+sbbh+"\"}";
    	RequestBody body = RequestBody.create(JSON, json);
    	System.out.println("put json body");
    	//jwt 授权 header生成
    	String header = "Bearer "+token;
    	Request request = new Request.Builder()
    		.url(url)
    		//在http header里增加授权信息
    		.header("Authorization", header)
    		.put(body)
    		.build();
    	System.out.println(request);
    	System.out.println("-------------headers-----------");
    	System.out.println(request.headers());
    	System.out.println("-------------------------------");
    	Response response = c.newCall(request).execute();
    	System.out.println("response "+response);
	}

	private void loadProp() throws IOException{
		java.io.InputStream is = new java.io.FileInputStream("build/resources/main/"+PROP_FILE);
		prop.load(is);
		System.out.println("------------ Properties -------------");
		System.out.println(prop);
		System.out.println("-------------------------------------");
		username = getFromProp(P_USERNAME);
		password = getFromProp(P_PASSWORD);
		baseurl = getFromProp(P_URL);
		is.close();
	}

	private String getFromProp(String name) throws IOException{
		Object target = prop.getProperty(name);
		target = target==null?"":target;
		return target.toString();
	}

	private void writeToProp(String name, String value) throws IOException{
		// System.out.println("write "+name+" = "+value);
		prop.setProperty(name, value);
		java.io.OutputStream os = new java.io.FileOutputStream("build/resources/main/"+PROP_FILE);
		prop.store(os, "Updated");
		os.close();
		System.out.println("properties saved");
	}

	public static void main(String[] args) throws IOException {
		String action = args[0];
		Main main = new Main();
		main.loadProp();
		if (ACTION_TOKEN.equals(action)) {
			main.getToken();
		} else if (ACTION_GET.equals(action)) {
			String code = args[1];
			main.getProject(code);
		} else if (ACTION_PUT.equals(action)) {
			String code = args[1];
			String sbbh = args[2];
			main.putProject(code, sbbh);
		}
 	}
}