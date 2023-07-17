/**
 * 회원정보 관련 처리
 */

var member = {
	// 태그별로 상태 확인
	tagStatus: function(tag, input) {
		if (tag.is("[name=userpw]")) return this.userpwStatus(tag.val(), input);
		else if (tag.is("[name=userpw_ck]")) return this.userpwCheckStatus(tag.val());
		else if (tag.is("[name=userid]")) return this.useridState(tag.val());
		else if (tag.is("[name=email]")) return this.emailState(tag.val());
	},

	//	아이디 입력 상태 확인 : 영문소문자나, 숫자만
	useridState: function(id) {
		var reg = /[^a-z0-9]/g;
		if (id == "") {
			return this.common.empty;
		} else if (id.match(this.space)) {
			return this.common.space;
		} else if (reg.test(id)) {
			return this.userid.invalid;
		} else if (id.length < 5) {
			return this.common.min;
		} else if (id.length > 10) {
			return this.common.max;
		} else {
			return this.userid.valid;
		}
	},

	// 이메일 입력 상태 확인
	emailState: function(email) {// abc123@asdf.com , abc123@kkkk.co.kr, abc123@asdf.net
		var reg = /[a-z0-9]+@[a-z]+\.[a-z]{2,3}/;
		if (email == "") {
			return this.common.empty;
		} else if (reg.test(email)) {
			return this.email.valid;
		} else {
			return this.email.invalid;
		}
	},

	email: {
		valid: { is: true, desc: '유효한 이메일 입니다.' },
		invalid: { is: false, desc: "유효하지 않은 이메일입니다." },
	},


	userid: {
		valid: { is: true, desc: '아이디 중복확인을 해주세요.' },
		invalid: { is: false, desc: "영문 소문자나 숫자만 입력이 가능합니다" },
		usable: { is: true, desc: "사용가능한 아이디 입니다." },
		unUsable: { is: false, desc: "이미 사용중인 아이디 입니다." }
	},

	common: {
		empty: { is: false, desc: '입력하세요' },
		space: { is: false, desc: '공백없이 입력하세요' },
		min: { is: false, desc: '5자 이상을 입력하세요' },
		max: { is: false, desc: '10자 이하를 입력하세요' }
	},

	space: /\s/g,

	userpw: {
		invalid: { is: false, desc: '영문 대/소문자, 숫자만 입력하세요' },
		lack: { is: false, desc: '영문 대/소문자, 숫자를 모두 포함해야 합니다' },
		valid: { is: true, desc: '사용가능한 비밀번호 입니다' },
		equal: { is: true, desc: '비밀번호와 일치합니다' },
		notEqual: { is: false, desc: '비밀번호와 일치하지않습니다.' }
	},

	showStatus: function(target) {
		var status = this.tagStatus(target, true)
		console.log(status);
		target.closest('.input-check').find('.desc').text(status.desc)
			.removeClass('text-success text-danger')
			.addClass(status.is ? 'text-success' : 'text-danger')
	},

	//	비밀번호를 다시 입력할 때 비밀번호확인 항목을 초기화 하는 처리
	userpwStatus: function(pw, input) {
		if (input) {
			$('[name=userpw_ck]').val('');
			$('[name=userpw_ck]').closest('.input-check').find('.desc').text('').removeClass('text-success text-danger');
		}

		var reg = /[^A-Za-z0-9]/g, upper = /[A-Z]/g, lower = /[a-z]/g, digit = /[0-9]/g;
		if (pw == "") return this.common.empty;
		else if (pw.match(this.space)) return this.common.space;
		else if (reg.test(pw)) return this.userpw.invalid;
		else if (pw.length < 5) return this.common.min;
		else if (pw.length > 10) return this.common.max;
		else if (!upper.test(pw) || !lower.test(pw) || !digit.test(pw)) return this.userpw.lack;
		else return this.userpw.valid;
	},
	userpwCheckStatus: function(pwCheck) {
		if (pwCheck == "") return this.common.empty;
		else if (pwCheck == $('[name=userpw]').val()) return this.userpw.equal;
		else if (pwCheck != $('[name=userpw]').val()) return this.userpw.notEqual;
	}
}