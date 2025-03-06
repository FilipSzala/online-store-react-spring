import React, { useState, useEffect } from 'react'


const ProductImage = ({ productId }) => {
  const [productImage, setProductImage] = useState(null); // Poprawna nazwa zmiennej

  useEffect(() => {
      const fetchProductImage = async () => {
        try {
          const response = await fetch(
            `http://localhost:8080/api/v1/images/${productId}/attachment`
          );
          const blob = await response.blob();
          const reader = new FileReader();
          
          reader.onloadend = () => {
            setProductImage(reader.result); // Poprawiona nazwa zmiennej
          };
          
          reader.readAsDataURL(blob);
        } catch (error) {
          console.error("Error fetching image:", error);
        }
      };

      if (productId) {
        fetchProductImage();
      }
  }, [productId]);

  if (!productImage) return null; // Poprawiona nazwa zmiennej

  return (
    <div>
        <img src={productImage} alt='Product Image'/>
    </div>
  );
}

export default ProductImage;