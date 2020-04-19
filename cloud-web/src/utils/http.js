import Vue from 'vue'
import VueAxios from 'axios'
import Settings from '../settings'

const http = VueAxios.create({
    baseURL: Settings.baseURL,
    timeout: Settings.timeout,
    withCredentials: Settings.withCredentials
});

// request拦截
http.interceptors.request.use(
    request => {
        console.info("request:", request);
        let auth = Vue.cookie.get(Settings.authTokenKey);
        if (null != auth) {
            request.headers[Settings.authTokenKey] = auth;
        }
        return request;
    },
    error => {
        return Promise.reject(error);
    });

// response拦截
http.interceptors.response.use(
    response => {
        if (1 === 1) {
            console.info("response:{}", response);
        }
        return Promise.resolve(response);
    },
    error => {
        return Promise.reject(error);
    });

// 封装Get请求
export function get(url = '', params = {}) {
    return http({
        url,
        method: 'GET',
        headers: {},
        data
    });
}

// form提交
export function postForm(url = '', data = {}) {
    return http({
        url,
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
        },
        data
    });
}

// json提交
export function postJson(url = '', data = {}) {
    return http({
        url,
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        data
    });
}