import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from './Sidebar';
import Header from './Header';
import { FaBookOpen} from 'react-icons/fa';

function Home() {
    const subPages = [
        { icon: FaBookOpen, title: 'Portfolio 1'},
        { icon: FaBookOpen, title:  'Portfolio 2' },
    ];
    const name = "John Tan";
    const email = "john_tan@gmail.com";
  
    const currentPage = 'Portfolio 1'; 
  
    return (
      <div className="container-fluid" style={{backgroundColor: "#F8F9FD"}}>
        <div className="row">
          <Sidebar subPages={subPages} currentPage={currentPage} />
          <div className="col-md p-0" >
            <Header name={name} email={email}/>
            <div className='m-2'>
                <h1>Main Content</h1>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                </div>
          </div>
        </div>
      </div>
    );
  };
  
  export default Home;