import static net.grinder.script.Grinder.grinder
import static org.junit.Assert.*
import static org.hamcrest.Matchers.*
import net.grinder.script.GTest
import net.grinder.script.Grinder
import net.grinder.scriptengine.groovy.junit.GrinderRunner
import net.grinder.scriptengine.groovy.junit.annotation.BeforeProcess
import net.grinder.scriptengine.groovy.junit.annotation.BeforeThread
// import static net.grinder.util.GrinderUtils.* // You can use this if you're using nGrinder after 3.2.3
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

import org.ngrinder.http.HTTPRequest
import org.ngrinder.http.HTTPRequestControl
import org.ngrinder.http.HTTPResponse
import org.ngrinder.http.cookie.Cookie
import org.ngrinder.http.cookie.CookieManager

/**
* A simple example using the HTTP plugin that shows the retrieval of a single page via HTTP.
*
* This script is automatically generated by ngrinder.
*
* @author PerfTestUser
*/
@RunWith(GrinderRunner)
class TestRunner {

	public static GTest test
	public static HTTPRequest request
	public static Map<String, String> headers = [:]
	public static Map<String, Object> params = [:]
	public static List<Cookie> cookies = []
	public String body

	@BeforeProcess
	public static void beforeProcess() {
		HTTPRequestControl.setConnectionTimeout(6000)
		test = new GTest(1, "10.0.12.20")
		request = new HTTPRequest()

		// Set header data
		//headers.put("Content-Type", "application/x-www-form-urlencoded")
		headers.put("Cookie", "XXL_JOB_LOGIN_IDENTITY=7b226964223a312c22757365726e616d65223a2261646d696e222c2270617373776f7264223a226531306164633339343962613539616262653536653035376632306638383365222c22726f6c65223a312c227065726d697373696f6e223a6e756c6c7d; aops-sessionId=971e7582-8e8e-4694-882c-c491e3a8887b")
		headers.put("accountId","110")
		headers.put("userId","2")
		grinder.logger.info("before process.")
	}

	@BeforeThread
	public void beforeThread() {
		test.record(this, "test")
		grinder.statistics.delayReports = true
		grinder.logger.info("before thread.")
	}

	@Before
	public void before() {
		request.setHeaders(headers)
		CookieManager.addCookies(cookies)
		grinder.logger.info("before. init headers and cookies")
	}
	public void getCiList(){
		body="{\"accountId\":\"110\",\"modelIds\":[\"IP\"],\"pageNum\":1,\"pageSize\":200,\"userId\":\"2\"}"
		HTTPResponse response = request.POST("http://10.0.10.106:18555/api/v1/cmdb/consumer/business/getCiList?pageNum=1&pageSize=200", body.toString().getBytes())
		grinder.logger.info("-----返回响应的内容------"+response.getBodyText())

		if (response.statusCode == 301 || response.statusCode == 302) {
			grinder.logger.warn("Warning. The response may not be correct. The response code was {}.", response.statusCode)
		} else {
			assertThat(response.statusCode, is(200))
		}
	}

	@Test
	public void test() {
		getCiList()
	}
}
