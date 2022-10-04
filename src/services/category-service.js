import {myAxios} from './Helper.js'

export const loadAllCategories = () => {
    return myAxios
    .get('/categories/')
    .then(response=>{
        return response.data ;
    })
}