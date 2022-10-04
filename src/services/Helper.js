import axios from "axios" ;

export const BASE_URL = 'http://localhost:9090/api/v1' ;

// Axios Object Provider

export const myAxios = axios.create({
    baseURL : BASE_URL , // now we can use server Calling
})