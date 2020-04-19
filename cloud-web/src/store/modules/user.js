import VueCookie from 'vue-cookie';
import Settings from '../../settings';

const state = {
    token: VueCookie.get(Settings.authTokenKey)
}

const mutations = {
    SET_TOKEN: (state, token) => {
        state.token = token
    }
}

const actions = {
    // 登录
    login({commit}, userInfo) {
        const {username, password} = userInfo
        return new Promise((resolve, reject) => {
            login({username: username.trim(), password: password.trim()})
                .then(response => {
                    const {data} = response
                    commit('SET_TOKEN', data.token)
                    VueCookie.set(Settings.authTokenKey, token)(data.token)
                    resolve()
                }).catch(error => {
                reject(error)
            })
        })
    },

}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
