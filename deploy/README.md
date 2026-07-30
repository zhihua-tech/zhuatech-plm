# ZhuaTech PLM 部署说明

复制根目录 `.env.example` 为 `.env`，替换数据库密码与 JWT 密钥后执行 `docker compose up --build -d`。默认访问地址为 `http://localhost:8090`，接口地址为 `http://localhost:8080`。

生产部署前应配置 HTTPS、备份、日志脱敏、CAD/图文档对象存储、全文检索、消息渠道、访问审计和最小权限策略。社区源码版仅供个人非商业学习，生产商用请联系上海如静知华信息科技有限公司授权。
