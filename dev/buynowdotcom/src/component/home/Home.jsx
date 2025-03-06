import React, { useEffect, useState } from 'react'
import Hero from "../hero/Hero"
import Paginator from "../common/Paginator"
import { Card } from "react-bootstrap";
import {toast, ToastContainer} from "react-toastify"
import { getDistinctProductsByName } from '../services/ProductService';
import { Link } from 'react-router-dom';
import ProductImage from "../utils/ProductImage"
import { useSelector } from "react-redux";

const Home = () => {
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [errorMessage, setErrorMessage] = useState ([]);
  const searchQuery = useSelector((state)=> state.search.searchQuery||"")


  const [currentPage, setCurrentPage] = useState(1)
  const itemsPerPage = 10;

  useEffect (()=> {
    const getProducts = async() =>{
      try {
        const response = await getDistinctProductsByName();
        setProducts (response.data);
      } catch (error){
        setErrorMessage(error.errorMessage)
        toast.error(errorMessage)
      }
    }
    getProducts()
  }, []);

  useEffect (()=>{
    const results = products.filter((product) => {
      const name = product.name || "";
      const matchesQuery = name.toLowerCase().includes(searchQuery?.toLowerCase()||"")
      return matchesQuery;
    });
    setFilteredProducts (results);
  },[searchQuery,products])







  const paginate = (pageNumber) => setCurrentPage(pageNumber);
  const indexOfLastProduct = currentPage * itemsPerPage;
  const indexOfFirstProduct = indexOfLastProduct - itemsPerPage;
  const currentProducts = filteredProducts.slice(
    indexOfFirstProduct,
    indexOfLastProduct
  )
  const totalItems = filteredProducts.length;

  return (
    <>
      <Hero />
      <div className='d-flex flex-wrap justify-content-center p-5'>
        <ToastContainer/>
        <div className = "all-card">
      {currentProducts && currentProducts.map((product) => (
        <Card key={product.id} className="home-product-card">
          <Link to={"#"} className="link">
          <div className="image-container">
            {product.images.length > 0 && (
              <ProductImage productId = {product.images[0].id}/>
            )}
          </div>
          </Link>
          
          <Card.Body className="custom-card-body">
            <p className='product-description'>
              {product.name} 
              </p>
              <p className='product-description'>
              {product.brand}
              </p>
            <div className='price-container'> 
            <h4 className='price-main'>{product.price}</h4>
            <h2 className='price-rest'>,00 zł</h2>
            </div>
            <p className='text-success'>{product.inventory} szt w magazynie</p>
            <div className='shop-now'>
            <Link to={`products/${product.name}`} className='shop-now-button'>
              {" "}
              <p>Zobacz</p>
              </Link>
            </div>

          </Card.Body>
        </Card>
      )
      )}
      </div>
      <Paginator
        itemsPerPage={itemsPerPage}
        totalItems={totalItems}
        currentPage={currentPage}
        paginate={paginate}
      />
      </div>
      
    </>
  )
}

export default Home