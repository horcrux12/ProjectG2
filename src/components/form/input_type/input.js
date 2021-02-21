import React, { Component } from 'react';

class Input extends Component {
    // constructor(props) {
    //     super(props);
    //     this.state = {  }
    // }

    handleChange = (el) => {
        this.props.inputProp.funcSet(el)
    }

    render() { 
        const value = this.props
        if (value.checked != undefined) {
            return ( 
                <input type={value.inputProp.inputType} 
                    name={value.inputProp.inputname} 
                    className={value.inputProp.classes}
                    placeholder={value.inputProp.inputPh}
                    value={value.inputProp.inputVal} 
                    onChange={this.handleChange}
                    checked={value.checked}
                />
            );
        }else if (value.inputProp.inputType == "number"){
            return ( 
                <input type={value.inputProp.inputType} 
                    name={value.inputProp.inputname} 
                    className={value.inputProp.classes}
                    placeholder={value.inputProp.inputPh}
                    value={value.inputProp.inputVal} 
                    onChange={this.handleChange}
                    min={value.inputProp.minVal}
                />
            );
        }else{
            return ( 
                <input type={value.inputProp.inputType} 
                    name={value.inputProp.inputname} 
                    className={value.inputProp.classes}
                    placeholder={value.inputProp.inputPh}
                    value={value.inputProp.inputVal} 
                    onChange={this.handleChange}
                />
            );
        }
    }
}
 
export default Input;