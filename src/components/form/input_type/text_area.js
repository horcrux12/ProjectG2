import React, { Component } from 'react';

class Input extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }

    handleChange = (el) => {
        this.props.funcSet(el);
    }
    
    render() { 
        const value = this.props
        // console.log(value.value)
        return ( 
            <textarea rows={value.inputRows} onChange={this.handleChange} className={this.class} name={value.inputname} value={value.value} placeholder={value.inputPc}></textarea>   
        );
    }
}
 
export default Input;