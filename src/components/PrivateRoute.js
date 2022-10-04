import React from 'react'
import { Outlet, Navigate } from 'react-router-dom'
import { isLoggedIn } from '../Auth'

export default function PrivateRoute() {

    return isLoggedIn() ? <Outlet/> : <Navigate to={"/login"}/>
    
}
