import React from "react";
import { useState } from "react";
import { useEffect } from "react";
import { toast } from "react-toastify";
import { signUp } from "../services/user-service";

export default function Signup(props) {
  const [data, setData] = useState({
    name: "",
    email: "",
    password: "",
    about: "",
  });

  // const [error, setError] = useState({
  //   errors: {},
  //   isError: false,
  // });


  useEffect(() => {
    console.log("Use Effect Data  : ",data);
  }, [data]);

  const handleChange = (event, property) => {
    // console.log("name") ;
    setData({ ...data, [property]: event.target.value });
  };

  // Resetting the From
  const resetData = () => {
    setData({
      name: "",
      email: "",
      password: "",
      about: "",
    });
  };

  

  // Submit the Form
  const submitForm = (event) => {
    event.preventDefault(); // to prevent from submit and after reload

    // if (error.isError) {
    //   console.log(data);
    //   toast.error("Please Recheck The Entered Data !!");
    //   return;
    // }

    // Data Validate

    // Call Server API for Sending Data

    signUp(data)
      .then((response) => {
        console.log("RESPONSE OF SUBMIT FORM : ", response);
        toast.success("Registration Successful");
        resetData();
      })
      .catch((error) => {
        toast.error("Please Recheck The Entered Data !!");
        console.log("ERROR IS : ", error);
      });
  };

  return (
    <div>
      <div className="container my-4 col-md-6 mt-10">
        <h2 className="my-3">Fill The Details Below To SignUp</h2>
        <hr />
        <form>
          <div className="mb-3 ">
            <label htmlFor="name" className="form-label">
              Name
            </label>
            <input
              type="text"
              className="form-control"
              id="name"
              placeholder="Enter Your Name Here"
              onChange={(e) => handleChange(e, "name")}
              value={data.name}
         //    invalid = {error.errors?.response?.data?.name ? true : false}
            />
          </div>
          <div className="mb-3 ">
            <label htmlFor="emailAddress" className="form-label">
              Email address
            </label>
            <input
              type="email"
              className="form-control"
              id="emailAddress"
              placeholder="Enter Your Email ID"
              onChange={(e) => handleChange(e, "email")}
              value={data.email}
            />
          </div>
          <div className="mb-3">
            <label htmlFor="exampleInputPassword1" className="form-label">
              Password
            </label>
            <input
              type="password"
              className="form-control"
              id="exampleInputPassword1"
              placeholder="Enter A Password"
              onChange={(e) => handleChange(e, "password")}
              value={data.password}
            />
          </div>

          <div className="form-group mb-3">
            <label htmlFor="exampleFormControlTextarea1">
              Something About Yourself
            </label>
            <textarea
              className="form-control"
              id="exampleFormControlTextarea1"
              rows="4"
              onChange={(e) => handleChange(e, "about")}
              value={data.about}
            ></textarea>
          </div>
          <button
            type="submit"
            className={`btn btn-primary container my-2`}
            onClick={submitForm}
          >
            Register <span>✒️</span>
          </button>
          <button
            type="reset"
            className={`btn btn-warning container my-2`}
            onClick={resetData}
          >
            Reset <span>🚮</span>
          </button>
        </form>
      </div>
    </div>
  );
}
