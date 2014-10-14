package com.acme.flight.analysis.rule;

import com.acme.flight.analysis.model.ArrivalInfo;

public class OrRule implements Rule {

	private Rule firstRule;
	private Rule secondRule;

	public OrRule(Rule firstRule, Rule secondRule) {
		this.firstRule = firstRule;
		this.secondRule = secondRule;
	}

	@Override
	public boolean accepts(ArrivalInfo arrival) {
		return firstRule.accepts(arrival) || secondRule.accepts(arrival);
	}

}