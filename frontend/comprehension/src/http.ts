import axios, {AxiosInstance, AxiosRequestConfig, AxiosResponse} from 'axios';
import {URL_BASE} from "~/constants";

export class Request {
    private instance: AxiosInstance | undefined

    constructor(config: AxiosRequestConfig) {
        this.instance = axios.create(config)
    }

    request(config: AxiosRequestConfig): Promise<AxiosResponse> {
        return new Promise<AxiosResponse>((resolve, reject) => {
            this.instance?.request(config)
                .then((res) => {
                    resolve(res)
                })
                .catch((err) => {
                    reject(err)
                })
        })
    }
}

let url = URL_BASE || window.location.href
console.log(url)
export const remote: Request = new Request({
    baseURL: url
})

export const validatePasscode = (passcode: string): Promise<boolean> => {
    return new Promise((resolve, reject) => {
        remote.request({
            method: "get",
            url: "api/v1/passcode/validate",
            params: {
                passcode: passcode
            }
        }).then((res) => {
            resolve(res.status == 200 && res.data.toString() === "true")
        }).catch(reason => reject(reason))
    })
}