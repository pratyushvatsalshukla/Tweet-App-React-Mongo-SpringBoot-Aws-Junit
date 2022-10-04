import "./NavBar.css";
import React from "react";
import { Link } from "react-router-dom";
import { doLogout, getCurrentUserDetail, isLoggedIn } from "../Auth";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
export default function NavBar(props) {

  let navigate = useNavigate() ;

  const [isOpen, setisOpen] = useState(false);
  const [login, setlogin] = useState(false);
  const [user, setUser] = useState(null);

  useEffect(() => {
    setlogin(isLoggedIn());
    setUser(getCurrentUserDetail());
  }, [login]);

  const logOut  = ()=>{
    doLogout(()=>{
      setlogin(false) ;
      navigate("/about") ;
    }) ;
  }

  return (
    <nav className={`navbar navbar-expand-lg bg-dark navbar-dark`}>
      <div className="container-fluid">
        <Link
          className="navbar-brand mx-4"
          to="/"
          style={{
            fontFamily: "'Brush Script MT', cursive",
            color: "blue",
            fontSize: "3vh",
          }}
        >
          {" "}
          TweetApp
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0 mx-3">
            <li className="nav-item mx-4">
              <Link className="nav-link" to="/home">
                Home
              </Link>
            </li>

            <li className="nav-item mx-4">
              <Link className="nav-link" to="/about">
                About
              </Link>
            </li>
            <li className="nav-item mx-4">
              <Link className="nav-link" to="/services">
                Services
              </Link>
            </li>
            <li className="nav-item dropdown">
              <a
                className="nav-link dropdown-toggle"
                href="#"
                id="navbarDropdown"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                More
              </a>
              <ul className="dropdown-menu" aria-labelledby="navbarDropdown">
                <li>
                  <a className="dropdown-item" href="#">
                    Action
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" href="#">
                    Another action
                  </a>
                </li>
                <li>
                  <hr className="dropdown-divider" />
                </li>
                <li>
                  <a className="dropdown-item" href="#">
                    Something else here
                  </a>
                </li>
              </ul>
            </li>
          </ul>

          <ul className="navbar-nav  mb-2 mb-lg-0 float-end">
            {login && (
              <>
                <li className="nav-item mx-4">
                  <a className="nav-link" aria-current="page" onClick={logOut} target="_blank">
                    LogOut
                  </a>
                </li>
                <li className="nav-item mx-4">
                  <div className="nav-link" aria-current="page">
                    {user.name}
                  </div>
                </li>
              </>
            )}
          </ul>

          <ul className="navbar-nav  mb-2 mb-lg-0 float-end">
            {!login && (
              <>
                <li className="nav-item mx-4">
                  <Link className="nav-link" aria-current="page" to="/login">
                    Login
                  </Link>
                </li>
                <li className="nav-item mx-4">
                  <Link className="nav-link" to="/signup">
                    SignUp
                  </Link>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
}
