import {createApp} from 'vue';
import {createRouter, createWebHistory} from 'vue-router'
import "~/styles/index.scss";
import "uno.css";
import "element-plus/theme-chalk/src/message.scss";
import App from "./App.vue";

const app = createApp(App)
export const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            name: 'login',
            component: () => import("~/components/pages/Login.vue")
        },
        {
            path: '/test',
            name: 'test',
            component: () => import("~/components/pages/Test.vue")
        }
    ]
})
app.use(router)
app.mount("#app")

//utils
const passcodeRegex: RegExp = /[A-Z]{6}[0-9]{6}/g
export const checkPassCode = (passcode: string | null | undefined): boolean => {
    if (passcode == undefined) return false;
    return passcode.match(passcodeRegex) != undefined;
}