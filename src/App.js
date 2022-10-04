import "./App.css";
import NavBar from "./components/NavBar";
import React from "react";
import Login from "./components/Login";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Signup from "./components/Signup";
import About from "./components/About" ;
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import UserDashboard from "./components/private-routes/UserDashboard";
import PrivateRoute from "./components/PrivateRoute";
import ProfileInfo from "./components/private-routes/ProfileInfo";

function App() {

  return (
    <>
      <Router>
        <ToastContainer position="bottom-center"/>
        <NavBar />
        <div className="container App" style={{backgroundImage:`url("../src/resources/bgImage.jpg")`}}>
          <Routes>
            <Route exact path="/login" element={<Login  />} />
            <Route exact path="/signup" element={<Signup  />} />
            <Route exact path="/about" element={<About  />} />
            <Route exact path ="/user" element={<PrivateRoute/>}>
              <Route exact path ="dashboard" element={<UserDashboard/>}/>
              <Route exact path ="profile-info" element={<ProfileInfo/>}/>

            </Route>
            </Routes>

        </div>
       
      </Router>
    </>
  );
}

export default App;
