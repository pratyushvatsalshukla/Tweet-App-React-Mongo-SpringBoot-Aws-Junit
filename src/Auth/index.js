// isLoggedIn ?? ==> Token in local Storage => Logged In

import { toast } from "react-toastify";

export const isLoggedIn = () =>{

    if(localStorage.getItem("data") == null)
   {
       return false ;
   }
   else{
       return true ;
   }

}

// doLogIn => setts the data to local Storage

export const doLogin = (data, next) =>{
    localStorage.setItem("data", JSON.stringify(data)) ;
    next() ;
}

// doLogOut = > remove from localstorage

export const doLogout = (next) =>{
     (localStorage.removeItem("data") !== null) ? localStorage.removeItem("data") : toast.error("Token Data Unavailable in Local Storage !!") ;
     next() ;
}

// Get Current User Detail.

export const getCurrentUserDetail = () =>{
    return isLoggedIn() ? JSON.parse(localStorage.getItem("data")).user : undefined ; // False means user isnt logged In !!

}