import React, {useState} from 'react'
import HeroSlider from './HeroSlider';
import SearchBar from '../search/SearchBar';
import { setSearchQuery } from '../../store/features/searchSlice';
import { useDispatch } from 'react-redux';

const Hero = () => {
  const dispatch = useDispatch();

  const [currentSlide]= useState(0);
  return (

    <div className='hero'>
      <HeroSlider />
  <div className='hero-content'>
            <h1>
                Witamy w sklepie <span className='text-primary'> samrazwczas</span>
                <SearchBar onChange={(e)=> dispatch(setSearchQuery(e.target.value))}/>
            </h1>

            <div className = "home-button-container">
                <a href="#" className="home-shop-button link">
                  Shop now 
                </a>
                <button className="deals-button">Today's Deal</button>
            </div>
        </div>

    </div>
  )
}

export default Hero