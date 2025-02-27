import React from 'react'
import {Outlet} from "react-router-dom"
import NavBar from "../layout/NavBar"
import Footer from "../layout/Footer"

const RootLayout = () => {
  return (
    <div>
        <NavBar/>
        <Outlet/>
        <Footer />
    </div>
  )
}

export default RootLayout