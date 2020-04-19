import {postForm} from '../utils/http'

export function login(data) {
    return postForm(
        '/login',
        data
    );
}