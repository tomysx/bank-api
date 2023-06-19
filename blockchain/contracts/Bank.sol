pragma solidity ^0.8.19;

contract Bank {
    mapping(address => uint256) public balances;

    function deposit() public payable {
        require(msg.value > 0);
        balances[msg.sender] += msg.value;
    }

    function withdraw(uint256 amount) public {
        require(amount <= balances[msg.sender], "Insufficient balance");
        address payable recipient = payable(msg.sender);
        balances[msg.sender] -= amount;
        recipient.transfer(amount);
    }

    function getBalance() public view returns (uint256) {
        return balances[msg.sender];
    }
}
