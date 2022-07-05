# Charlie Show
#更新汇总
### 2022年7月5日17:14
- [修改] WecomTemplateData接收前端数据,修改成VO类放到admin下
- [新增] app模块添加saveApp方法
- [新增] app模块添加queryAllApps方法
## 2022年7月5日16:24
- [修改] TaskData接收前端数据,删除字段triggerTime 和审核状态
- [修改] TaskData接收前端数据,修改名称TaskVO
- [修改] TaskVO迁移到admin项目,在domain层需要assenble封装,不能直接接收
## 2022年7月3日14:21
- [修改]mnmp-commons 基础包不引用spring等依赖,迁移到admin
## 2022年7月3日13:51
- [修改] 模板接口修改大批参数方法,引入对象封装WecomTemplateData
## 2022年7月3日9:51
- [修改] 新增任务，修改任务提交文件名称，和文本框参数通知人群
## 2022年7月3日9:21
- [修改] taskApi接口的新增任务,修改任务参数抽取成对象
- [删除] 去除appApi接口的根据用户查询应用功能.
