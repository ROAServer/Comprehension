<script setup lang="ts">
import type {
  FormRules,
  FormItemRule,
} from "element-plus";
import {Ref, ref} from "vue";

interface Form {
  passcode: string
}

const login = () => {
  //isLoginRunning.value = true
  let passcode: String = loginForm.value.passcode
  if (passcode == "") return
}

const isLoginRunning = ref<boolean>(false);

const loginForm: Ref<Form> = ref<Form>({
  passcode: ""
})

const PASSCODE_RULES: FormItemRule[] = [
  {
    required: true,
    message: "需要考试码",
    trigger: "blur",
  },
  {
    pattern: "[A-Z]{6}[0-9]{6}",
    message: "无效的考试码",
    trigger: "blur",
  },
];

const LOGIN_FORM_RULES: Ref<FormRules<Form>> = ref({
  passcode: PASSCODE_RULES,
});


</script>

<template>
  <div>
    <el-card id="box" stretch :model="loginForm" title="使用考试码登录">
      <el-text style="font-weight: 1000">使用考试码登录</el-text>
      <div style="display: flex;margin-top: 20px;flex-direction: row">
        <el-text style="text-align: left;width: 60px">考试码</el-text>
        <el-input placeholder="" style="margin-left: 20px" v-model="loginForm.passcode" @keyup.enter="login"/>
      </div>
      <el-button style="margin-top: 20px" class="button" :loading="isLoginRunning" @click="login">登录</el-button>
    </el-card>
  </div>

</template>

<style scoped lang="scss">
$shadow-light: 0 0px 0px 0 rgba(0, 0, 0, 0.2);


#box {
  width: 20rem;
  min-height: 10rem;
  margin: 10rem auto;
  padding: 1rem;

  background: transparent;
  box-shadow: $shadow-light;
  border-color: transparent;

  //border-radius: 10px;

  @media only screen and (width < 480px) {
    width: 16rem;
  }

  .button {
    align-self: center;
  }
}
</style>