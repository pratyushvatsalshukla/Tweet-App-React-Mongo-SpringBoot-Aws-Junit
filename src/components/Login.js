import { useState, useEffect } from "react";
import React from "react";
import "./Login.css";
import { toast } from "react-toastify";
import { loginUser } from "../services/user-service";
import { doLogin, doLogout } from "../Auth";
import { useNavigate } from "react-router-dom";

export default function Login() {

  const navigate = useNavigate() ;

  const [loginDetail, setLoginDetail] = useState({
    username: "",
    password: "",
  });

  const resetForm = () => {
    setLoginDetail({
      username: "",
      password: "",
    });
  };

  useEffect(() => {
   // console.log("Use Effect Data : ", loginDetail);
  }, [loginDetail]);

  const handleChange = (event, fields) => {
    // event.target.value == 'username' ? setLoginDetail({username:event.target.value}) : setLoginDetail({password:event.target.value})
    let actualValue = event.target.value;
    setLoginDetail({
      ...loginDetail,
      [fields]: actualValue,
    });
  };



  const handleFormSubmit = (event) => {
    event.preventDefault();
    console.log(loginDetail);

    if(loginDetail.username.trim() ==='')
    {
        toast.error("Please enter your Username")
        return ;
    }
   else if(loginDetail.password.trim() ===''){
        toast.error("Please Enter Your Password")
        return ;
    }

    // Submit Data to server in order to generate the token

    loginUser(loginDetail).then((data)=>{      // data is equal to jetToken
        console.log("Logged In !!", data) ;
        

        // Save Data to Local Storage

        doLogin(data, ()=>{
            console.log("Login Details Saved To Local Storage via doLogin Method") ;
        })

        // Redirect To User DashBoard !!

        navigate("/user/dashboard") ;
        window.location.reload(false);




    }).catch((error)=>{
        console.log("Somthing Wrong on Server " ,error) ;
        toast.error(error.response.data.message)
    })


  };



  return (
    <div>
      <h1 className="container my-5">
        Please enter the details below to login !!
      </h1>
      <div className="container my-4 col-md-6 mt-10">
        <form onSubmit={handleFormSubmit}>
          <div className="mb-3 ">
            <label htmlFor="emailAddress" className="form-label">
              Email address
            </label>
            <input
              type="email"
              className="form-control"
              id="emailAddress"
              aria-describedby="emailHelp"
              value={loginDetail.username}
              onChange={(e) => handleChange(e, "username")}
            />
            <div id="emailHelp" className="form-text">
              We'll never share your email with anyone else.
            </div>
          </div>
          <div className="mb-3">
            <label htmlFor="exampleInputPassword1" className="form-label">
              Password
            </label>
            <input
              type="password"
              className="form-control"
              id="exampleInputPassword1"
              value={loginDetail.password}
              onChange={(e) => handleChange(e, "password")}
            />
          </div>
          <div id="emailHelp" className="form-text">
            We'll never share your password with anyone else.
          </div>

          <button type="submit" className={`btn container btn-primary my-3`}>
            Sign In <span>✒️</span>
          </button>

          <button type="reset" className={`btn container btn-danger my-3`} onClick={resetForm}>
            Reset <span>🚮</span>
          </button>
        </form>
      </div>
    </div>
  );
}
