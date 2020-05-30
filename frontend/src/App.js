import React, { Component } from "react";
import AddCredential from "./Components/AddCredential";
import { Route, BrowserRouter as Router } from "react-router-dom";
import Table from "./Components/Table";
import Register from "./Components/Register";

class App extends Component {
    render() {
        return (
            <Router>
                <Route exact path="/register" component={Register} />
                <Route exact path="/" component={AddCredential} />
                <Route exact path="/view" component={Table} />
            </Router>
        );
    }
}

export default App;
