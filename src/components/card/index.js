import React, { Component } from 'react'
import "./style.css"

class Card extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() { 
        const {imgSource, alt, desc} = this.props
        return ( 
            <div className="card">
                <img src={imgSource} alt={alt}/>
                <div className="description">
                    {desc}
                </div>
            </div>
        );
    }
}
 
export default Card;