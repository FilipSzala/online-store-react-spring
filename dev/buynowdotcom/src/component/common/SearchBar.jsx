import React from 'react'

const SearchBar = () => {
    return (
        <div className='search-bar'>
            <select className="form-control-sm" name="category">
                <option value="all">All Category</option>
                <option value="tabs">Tabs</option>
                <option value="gadget">Gadget</option>
            </select>

            <input
                type="text"
                className="form-control-sm"
                name="search"
                placeholder="Search..."
            />

            <button className="search-button">Search</button>
        </div>


    )
}

export default SearchBar