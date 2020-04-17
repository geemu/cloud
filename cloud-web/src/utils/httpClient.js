import Vue from 'vue'
import axios from 'axios'
import router from '../router/index'
import Settings from '../settings'
import qs from 'qs'

const http = axios.create({
    timeout: Settings.timeout,
    withCredentials: true
})

// 请求拦截
http.interceptors.request.use(
    request => {
        request.headers[Settings.authTokenKey] = Vue.cookie.get(Settings.authTokenKey);
        return request;
    },
    error => {
        return Promise.reject(error);
    });

// 响应拦截
http.interceptors.response.use(
    response => {
        if (response.data && response.data.code === 401) {
            router.push({name: 'login'});
        }
        return response;
    },
    error => {
        return Promise.reject(error);
    });


/**
 * form提交
 * @param {Object} request [请求对象]
 */
export function postForm(request = {}) {
    return new Promise((resolve, reject) => {
        http.post(request.url, qs.stringify(request.data), {headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'}})
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                reject(error.data)
            })
    });
}

/**
 * json提交
 * @param {Object} request [请求对象]
 */
export function postJson(request = {}) {
    return new Promise((resolve, reject) => {
        http.post(request.url, JSON.stringify(request.data), {headers: {'Content-Type': 'application/json; charset=utf-8'}})
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                reject(error.data)
            })
    });
}


export default http
