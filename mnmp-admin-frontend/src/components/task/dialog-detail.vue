<template>
  <dev class="cus-main-wrap">
    <el-form ref="form" :model="form" label-width="150px">
      <el-form-item label="任务名称" >
        <el-input v-model="form.name" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="任务状态">
        <el-input v-if="form.taskStatus === 0" value="未开启" :disabled="true"></el-input>
        <el-input v-if="form.taskStatus === 1" value="推送中" :disabled="true"></el-input>
        <el-input v-if="form.taskStatus === 2" value="终止" :disabled="true"></el-input>
        <el-input v-if="form.taskStatus === 3" value="已结束" :disabled="true"></el-input>
        <el-input v-if="form.taskStatus === 4" value="失败" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="消息类型">
        <el-input v-if="form.noticeType === 1" value="SMS" :disabled="true"></el-input>
        <el-input v-if="form.noticeType === 2" value="EMAIL" :disabled="true"></el-input>
        <el-input v-if="form.noticeType === 3" value="WECHAT" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="mock">
        <el-input v-if="form.mock === 0" value="否" :disabled="true"></el-input>
        <el-input v-if="form.mock === 1" value="是" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="所属模板">
        <el-input v-model="form.templateName" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="所属签名">
        <el-input v-model="form.signName" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="所属应用">
        <el-input v-model="form.appName" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="周期类型" v-if="cycleShow">
        <el-input v-if="form.cycleLevel === 0" value="分钟" :disabled="true"></el-input>
        <el-input v-if="form.cycleLevel === 1" value="小时" :disabled="true"></el-input>
        <el-input v-if="form.cycleLevel === 2" value="日" :disabled="true"></el-input>
        <el-input v-if="form.cycleLevel === 3" value="周" :disabled="true"></el-input>
        <el-input v-if="form.cycleLevel === 4" value="月" :disabled="true"></el-input>
        <el-input v-if="form.cycleLevel === 5" value="年" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="周期数" v-if="cycleShow">
        <el-input v-model="form.cycleNum" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="首次触发时间">
        <el-input v-model="form.firstTriggerTime" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="下次任务触发时间">
        <el-input v-model="form.nextTriggerTime" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="任务触发结束时间">
        <el-input v-model="form.triggerEndTime" :disabled="true"></el-input>
      </el-form-item>


      <el-form-item label="描述" prop="remark">
        <el-input v-model="form.remark" type="textarea" disabled/>
      </el-form-item>

      <el-form-item label="错误日志" prop="error">
        <el-input v-model="form.error" type="textarea" disabled/>
      </el-form-item>

      <el-form-item label="目标文件名称">
        <el-input v-model="form.targetFileName" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="目标文件地址">
        <template v-if="form.targetFileUrl">
          <i class="el-icon-download"  @click="downExcel(form.targetFileUrl)"></i>
        </template>
      </el-form-item>

      <el-form-item>
        <el-button type="danger" size="small" @click="pre()">
          返回
        </el-button>
      </el-form-item>
    </el-form>
  </dev>
</template>
<script>
import {detail, downExcel} from '@/api/task.js'
import {downloadFileByBlob} from "@/utils/download-file";
export default {
  data() {
    return {
      cycleShow: false,
      form: {
        id: null
      }
    }
  },
  mounted() {
    this.cycleShow = false
    this.getDetail(this.$route.query.id)
  },
  methods: {
    getDetail(_id) {
      detail({ id: _id })
        .then(res => {
          this.form = res
          if (res.taskType === 2) {
            this.cycleShow = true
          }
        })
        .catch(err => {
          console.dir(err)
        })
    },
    downExcel(path) {
      downExcel({ path: path }, { responseType: 'blob' }).then(res => {
        downloadFileByBlob(res.data, res.headers['content-excelname'], res.data.type)
      })
    },
    pre() {
      this.$router.push({ name: 'task' })
    }
  }
}
</script>
