<script setup lang="ts">
import {
  FormRules,
  FormItemRule, ElMessage,
} from "element-plus";
import {Ref, ref} from "vue";

interface Form {
  passcode: string
}

const passcodeRegex: RegExp = /[A-Z]{6}[0-9]{6}/g
const isLoginRunning = ref<boolean>(false);
const loginForm: Ref<Form> = ref<Form>({
  passcode: ""
})

const login = () => {
  isLoginRunning.value = true
  let passcode: String = loginForm.value.passcode
  if (passcode == "" || !passcode.match(passcodeRegex)) {
    ElMessage.error("无效的考试码")
  } else {

  }
  isLoginRunning.value = false
}

</script>

<template>
  <div>
    <el-card id="box" stretch :model="loginForm">
      <el-text style="font-weight: 1000">使用测试码登录</el-text>
      <div style="display: flex;margin-top: 20px;flex-direction: row">
        <el-text style="text-align: left;width: 60px">测试码</el-text>
        <el-input placeholder="" style="margin-left: 10px" v-model="loginForm.passcode" @keyup.enter="login"/>
      </div>
      <el-button style="margin-top: 20px" class="button" :loading="isLoginRunning" @click="login">登录</el-button>
    </el-card>
  </div>

</template>

<style scoped lang="scss">
#box {
  width: 20rem;
  min-height: 10rem;
  margin: 10rem auto;
  padding: 1rem;

  background: transparent;
  box-shadow: 0 0 0 0 rgba(0, 0, 0, 0.2);
  border-color: transparent;

  @media only screen and (width < 480px) {
    width: 16rem;
  }

  .button {
    align-self: center;
  }
}
</style>