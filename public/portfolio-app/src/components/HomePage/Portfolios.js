
function Portfolios({ PortfolioData }) {
  return (
    <div class="tbl-container bdr">
      <table class="table">
        <thead class="bg-red">
          <tr>
            <th scope="col" className="text-muted fw-bolder">
              Name
            </th>
            <th scope="col" className="text-muted fw-bolder">
              Balance
            </th>
            <th scope="col" className="text-muted fw-bolder">
              Change
            </th>
          </tr>
        </thead>
        <tbody>
            {PortfolioData.map((Portfolio) => (
                <tr>
                <td>{Portfolio.name}</td>
                <td>{Portfolio.capitalAmount}</td>
                <td>{Portfolio.change/Portfolio.capitalAmount}%</td>
              </tr>
            ))}
        </tbody>
      </table>
    </div>
  );
}

export default Portfolios;
