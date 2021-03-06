class Token{
	var fingerprint : int;
	var securityclearance : int;
	var valid : bool;
	
	constructor init(finger : int, clearance : int)
		modifies this;
		ensures fingerprint == finger;
		ensures securityclearance == clearance;
		ensures valid == true;
		{
			valid := true;
			fingerprint := finger;
			securityclearance := clearance;
		}
	
	method cancelToken()
		modifies `valid;
		requires valid == true;
		ensures valid == false;
		{
			valid := false;
		}
}

class EnrollmentStation{
	var enrolledUsers : map<int, User>;
	
	method init()
		modifies this;
		ensures |enrolledUsers| == 0;
		{
			enrolledUsers := map[];
		}
		
	method createUser(fingerprint : int, clearance : int) returns (user : User)
		modifies `enrolledUsers;
		requires fingerprint !in enrolledUsers;
		ensures fingerprint in enrolledUsers;
		ensures enrolledUsers[fingerprint] == user;
		ensures user != null;
		ensures user.fingerprint == fingerprint;
		ensures user.token != null;
		ensures user.token.fingerprint == fingerprint;
		{
    		var newToken := new Token.init(fingerprint, clearance);
    		var newUser := new User.init(fingerprint, newToken);
    		enrolledUsers := enrolledUsers[fingerprint := newUser];
    		return newUser; 
		}
}


class IDStatation{
	var requiredsecurity : int;
	var alarmState : bool;
	var doorState : bool;
	
	constructor init(requiredsecurity : int)
		modifies this;
		requires requiredsecurity == 3 || requiredsecurity == 2 || requiredsecurity == 1;
		ensures this.requiredsecurity == requiredsecurity && !alarmState && !doorState;
		{
			this.requiredsecurity := requiredsecurity;
			alarmState := false;
			doorState := false;
		}
		
	method requestAccess(user : User, scannedFP : int) returns (valid : bool)
		modifies user.token`valid, this`alarmState, this`doorState; 
		requires user != null;
		requires user.token != null;
		requires user.token.valid == true;
		requires user.token.fingerprint == user.fingerprint;
		requires user.token.securityclearance >= requiredsecurity;
		ensures old(!user.token.valid || user.token.fingerprint!=scannedFP) ==> !valid && !user.token.valid && alarmState && !doorState;
		ensures old(user.token.valid && user.token.fingerprint==scannedFP && user.token.securityclearance >= requiredsecurity) ==> valid && !alarmState && user.token.valid && doorState;
		ensures old(user.token.valid && user.token.fingerprint==scannedFP && user.token.securityclearance < requiredsecurity) ==> !valid && alarmState && doorState && user.token.valid;
		{
 			var token := user.token;
			if (token.valid) {
				if (token.fingerprint == scannedFP) {
					if (token.securityclearance >= requiredsecurity) {
						alarmState := false;
						doorState := true;
						valid := true;
						return;
					}
					alarmState := true;
					doorState := false;
					valid := false;
					return;   
				} else {
					user.token.valid := false;
					alarmState := true;
					doorState := false;
					valid := false;
					return;
				}
			}
		}
  
	method closeDoor()
		modifies `doorState;
		requires doorState == true;
		ensures doorState == false;
		{
			doorState := false;
		}
}

class User{
	var fingerprint: int;
	var token: Token

	constructor init(fingerprint : int, t : Token)
		modifies this;
		ensures this.fingerprint == fingerprint;
		ensures this.token == t;
		{
			this.fingerprint := fingerprint;
			this.token := t;
		}
}

class Test {
	method main()
	{
		var boolean : bool;
		var ERS := new EnrollmentStation.init();
		var user1LvL1 := ERS.createUser(1,1);
		var user2LvL2 := ERS.createUser(2,2);
		var user3LvL3 := ERS.createUser(3,3);
		boolean := user1LvL1.fingerprint in ERS.enrolledUsers;
		if(!boolean){}
		else{assert true;}
		boolean := user2LvL2.fingerprint in ERS.enrolledUsers;
		if(!boolean){}
		else{assert true;}
		boolean := user3LvL3.fingerprint in ERS.enrolledUsers;
		if(!boolean){}
		else{assert true;}
	}
 }