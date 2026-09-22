/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.http.MediaType; import org.springframework.test.web.servlet.MockMvc; import java.time.LocalDate; import java.util.regex.*; import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@SpringBootTest @AutoConfigureMockMvc class PlmApiIntegrationTests {
    @Autowired MockMvc mvc;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private String login() throws Exception {String json=mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"admin\",\"password\":\"admin123\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.data.user.role").value("ADMIN")).andReturn().getResponse().getContentAsString();Matcher m=Pattern.compile("\\\"token\\\":\\\"([^\\\"]+)\\\"").matcher(json);if(!m.find())throw new AssertionError("登录响应中缺少 token");return m.group(1);}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void adminCanReadDashboardAndBom() throws Exception {String token=login();mvc.perform(get("/api/plm/dashboard").header("Authorization","Bearer "+token)).andExpect(status().isOk()).andExpect(jsonPath("$.data.productCount").isNumber()).andExpect(jsonPath("$.data.activeChanges").value(4));mvc.perform(get("/api/plm/bom/PRD-SV750").header("Authorization","Bearer "+token)).andExpect(status().isOk()).andExpect(jsonPath("$.data.length()").value(5));}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void engineerCanCreateChange() throws Exception {String body="{\"title\":\"测试工程变更\",\"productCode\":\"PRD-SV750\",\"changeType\":\"设计变更\",\"reason\":\"集成测试验证变更创建流程\",\"owner\":\"测试员\",\"dueDate\":\""+LocalDate.now().plusDays(7)+"\",\"riskLevel\":\"低\"}";mvc.perform(post("/api/plm/changes").header("Authorization","Bearer "+login()).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isOk()).andExpect(jsonPath("$.data.status").value("草稿"));}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void adminCanGateHighRiskEngineeringChange() throws Exception {mvc.perform(post("/api/plm/change-impact").header("Authorization","Bearer "+login()).contentType(MediaType.APPLICATION_JSON).content("{\"changeNo\":\"ECN-20260801\",\"affectedParts\":12,\"affectedSuppliers\":2,\"openOrders\":8,\"safetyCritical\":true,\"rollbackReady\":false}")).andExpect(status().isOk()).andExpect(jsonPath("$.data.riskScore").value(100)).andExpect(jsonPath("$.data.decision").value("BLOCK")).andExpect(jsonPath("$.data.requiredReviews.length()").value(5));}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void anonymousRequestIsDenied() throws Exception {mvc.perform(get("/api/plm/products")).andExpect(status().isUnauthorized());}
}
