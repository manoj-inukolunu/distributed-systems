package vectorclock

import (
	"fmt"
)

type VectorClock struct {
	clock []int
	node  int
}

func New(numNodes int, nodeId int) VectorClock {
	vClock := VectorClock{make([]int, numNodes), nodeId}
	for i := 0; i < numNodes; i++ {
		vClock.clock = append(vClock.clock, 0)
	}
	return vClock
}

func (v *VectorClock) Event() {
	v.clock[v.node]++
}

func (v *VectorClock) SendMessage() {
	v.Event()
}

func max(a int, b int) int {
	if a > b {
		return a
	}
	return b
}

func (v *VectorClock) ReceiveMessage(vClock VectorClock) {
	for node, clock := range v.clock {
		v.clock[node] = max(clock, vClock.clock[node])
	}
	v.Event()
}

func (v *VectorClock) PrintClock() {
	for node, clock := range v.clock {
		fmt.Println(node, " ", clock)
	}
}
