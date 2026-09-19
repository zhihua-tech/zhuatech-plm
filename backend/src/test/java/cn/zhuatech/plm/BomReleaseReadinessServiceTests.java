/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.plm;import cn.zhuatech.plm.service.BomReleaseReadinessService;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class BomReleaseReadinessServiceTests{private final BomReleaseReadinessService s=new BomReleaseReadinessService();/**
                                                                                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                   */
@Test void blocksObsoletePartWithoutAlternate(){var r=s.evaluate(new BomReleaseReadinessService.Request(100,90,2,0,3,12,false));assertEquals("BLOCK",r.status());}/**
                                                                                                                                                                                                                                                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                     */
@Test void approvesCompleteBom(){var r=s.evaluate(new BomReleaseReadinessService.Request(100,100,0,0,0,2,true));assertEquals("READY",r.status());}}
