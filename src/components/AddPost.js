import React from "react";
import { loadAllCategories } from "../services/category-service";
import { useEffect, useState, useRef } from "react";

export default function AddPost() {

    const [categories, setCategories] = useState([

    ])

    useEffect(() => {
      
        loadAllCategories().then((data) => {
            console.log(data) ;
            setCategories(data) ;
        }).catch(error => {
            console.log(error) ;
        })
    
    }, [])  // no dependency =? runs on starting.

  return (
    <>
      <div
        className="container my-5"
        style={{ boxShadow: "0px 0px 20px 2px #4aab9a" }}
      >
        <h1 className="my-5"> What's on your mind ? </h1>
        <div className="mb-3 my-3">
          <label
            htmlFor="exampleFormControlInput1"
            className="form-label"
            style={{ float: "left" }}
          >
            Post Title
          </label>
          <input
            type="text"
            className="form-control"
            id="exampleFormControlInput1"
            placeholder="Enter your post Ttitle here !!"
          />
        </div>
        <div className="mb-3 float-left my-3">
          <label
            htmlFor="exampleFormControlTextarea1"
            className="form-label"
            style={{ float: "left" }}
          >
            Post Content
          </label>
          <textarea
            className="form-control"
            id="exampleFormControlTextarea1"
            rows="4"
          ></textarea>
          {/* <JoditEditor
			ref={editor}
			value={content}
			config={config}
			tabIndex={1} // tabIndex of textarea
			onBlur={newContent => setContent(newContent)} // preferred to use only this option to update the content for performance reasons
			onChange={newContent => setContent(newContent)}
		/> */}
        </div>
        <div className="my-3">
          <select  className="form-select" aria-label="Default select example">
            <option defaultValue={1}>Select Options From Here</option>
            {
                categories.map((category)=>(
                    <option value={category.categoryId} key = {category.categoryId}>
                        {category.categoryTitle}
                    </option>
                ))
                
            }

          </select>
        </div>

        <button type="submit" className="btn btn-primary my-5">
          Create Post
        </button>
        <button type="reset" className="btn btn-danger my-3 mx-3">
          Reset Content
        </button>
      </div>
    </>
  );
}
